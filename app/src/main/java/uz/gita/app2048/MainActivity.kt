package uz.gita.app2048

import MovementDetector
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import uz.gita.app2048.contracts.TinyDB
import uz.gita.app2048.data.Movement
import uz.gita.app2048.presenter.GamePresenterImpl
import uz.gita.app2048.repository.GameRepositoryImpl

class MainActivity : AppCompatActivity(), GameContract.View {
    private val buttons: ArrayList<TextView> = ArrayList(16)
    private lateinit var presenter : GamePresenterImpl
    private var bestScore:Int=0

    private lateinit var tinyDB: TinyDB

    private lateinit var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private lateinit var textTime: Chronometer
    private lateinit var scoreText: TextView
    private var list= ArrayList<Int>(16)

    private var score = 0

    private var pauseTime: Long = 0
    private var isStarted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restart = findViewById<AppCompatImageButton>(R.id.restart)
        presenter = GamePresenterImpl(this, GameRepositoryImpl().apply {})
        tinyDB= TinyDB(this)
        restart.setOnClickListener { v: View? ->
            val builder =
                AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
            val customLayout =
                layoutInflater.inflate(R.layout.restart_chiqish, null)
            val yes1 = customLayout.findViewById<AppCompatButton>(R.id.btnPositive)
            yes1.setOnClickListener { b: View? -> restart()
            builder.dismiss()
            builder.hide()}
            val no1 = customLayout.findViewById<AppCompatButton>(R.id.btnNegative)
            no1.setOnClickListener { b: View? -> builder.hide()
            builder.dismiss()}
            builder.setView(customLayout)
            builder.setCancelable(false)
            builder.show()
        }

        val home = findViewById<AppCompatImageButton>(R.id.home)
        textTime= findViewById(R.id.text_time)
        textTime.base = SystemClock.elapsedRealtime()
        textTime.start()
        sharedPreferences = getSharedPreferences("GITA#", MODE_PRIVATE)

        home.setOnClickListener { v: View? ->
            val builder =
                AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
            val customLayout =
                layoutInflater.inflate(R.layout.menyu_chiqish, null)
            val yes1 = customLayout.findViewById<AppCompatButton>(R.id.btnPositive)
            yes1.setOnClickListener { b: View? -> finish()}
            val no1 = customLayout.findViewById<AppCompatButton>(R.id.btnNegative)
            no1.setOnClickListener { b: View? -> builder.hide() }
            builder.setView(customLayout)
            builder.setCancelable(false)
            builder.show()
        }

        loadViews()
        presenter.startGame()
        presenter.gameOver()

    }
    override  fun dialog() {

        val list=tinyDB.getListInt("Scores")
        list.add(score)
        tinyDB.putListInt("Scores",list)
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
        builder.setCancelable(false)
        val yes1 = customLayout.findViewById<TextView>(R.id.ok_button_alert)

        yes1.setOnClickListener { view1: View? ->


            scoreText.text = score.toString()

            presenter.setScore(score)
            builder.cancel()
            restart()
            finish()
        }
        val no1 = customLayout.findViewById<TextView>(R.id.cancel_button1)
        no1.setOnClickListener { view1: View? ->
            scoreText.text = score.toString()

            presenter.setScore(score)
            builder.dismiss()
            onRestart()
            builder.hide()
            restart()

        }
        builder.setView(customLayout)
        builder.show()

        textTime.stop()
    }


    private fun loadViews() {
        val mainContainer = findViewById<LinearLayout>(R.id.mainContainer)
        textTime = findViewById(R.id.text_time)
        val textScore = findViewById<TextView>(R.id.text_score)
        scoreText=findViewById(R.id.text_score)
        for (i in 0 until mainContainer.childCount) {
            val childContainer = mainContainer.getChildAt(i) as LinearLayout
            for (j in 0 until childContainer.childCount) {
                buttons.add(childContainer.getChildAt(j) as TextView)

            }
        }

        val movementDetector = MovementDetector(this)

        movementDetector.setOnMovementListener {
            when (it) {
                Movement.LEFT -> presenter.moveLeft()
                Movement.RIGHT ->presenter.moveRight()
                Movement.DOWN -> presenter.moveDown()
                Movement.UP -> presenter.moveUp()
            }
            presenter.gameOver()
            score=presenter.getScore()

            textScore.setText(score.toString())

        }

        mainContainer.setOnTouchListener(movementDetector)
    }

    @Override
    override fun onResume() {
        Log.d("yy",pauseTime.toString())
        pauseTime=sharedPreferences!!.getLong("pause_time",pauseTime)

        bestScore=tinyDB.getInt("BestScore")
        list=tinyDB.getListInt("list")
        if (list.size>0) {
            presenter.setList(list)}
        presenter.startGame()

        isStarted=sharedPreferences!!.getBoolean("is_started",isStarted)

        score=sharedPreferences.getInt("score",score)
        scoreText.text=score.toString()


        presenter.setScore(score)


        textTime.base = SystemClock.elapsedRealtime() + pauseTime
        textTime.start()


        super.onResume()
    }

    @Override
    override fun onPause() {

        pauseTime = textTime!!.base - SystemClock.elapsedRealtime()

        tinyDB.putListInt("list",list)
        if (bestScore<score){
            bestScore=score
            tinyDB.putInt("BestScore",bestScore)
            tinyDB.putLong("BestTime",pauseTime)
        }

        editor = sharedPreferences?.edit()

        editor!!.putLong("pause_time", pauseTime)
        editor!!.putBoolean("is_started", isStarted)
        editor!!.putInt("score", score)
        textTime.stop()
        Log.d("ff","uuuu")

        editor?.apply()
        super.onPause()
    }
    override fun changeState(matrix: Array<Array<Int>>) {

        list.clear()

        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                val button = buttons[4 * i + j]

                val value = matrix[i][j]
                list.add(value)
                if (value == 0) button.text = ""
                else button.text = matrix[i][j].toString()
                button.setBackgroundColor(
                    when (value) {
                        2 -> Color.parseColor("#E2D4C0")
                        4 -> Color.parseColor("#E58692")
                        8 -> Color.parseColor("#DCAB7C")
                        16 -> Color.parseColor("#F08B55")
                        32 -> Color.parseColor("#E87559")
                        64 -> Color.parseColor("#E9583A")
                        128 -> Color.parseColor("#E1C36D")
                        256 -> Color.parseColor("#90ABF2")
                        512 -> Color.parseColor("#F2E579")
                        1024 -> Color.parseColor("#E58692")
                        else -> Color.parseColor("#635E5E")
                    }
                )

            }

        }
    }

    override fun restart() {
        score=0
        presenter.setScore(score)
        scoreText.text=score.toString()

        pauseTime=0
        textTime.base = SystemClock.elapsedRealtime() + pauseTime
        textTime.start()


        list.clear()

        for (i in 0 ..15){
            list.add(0)
        }
        presenter.setList(list)
        presenter.restart()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val customLayout = layoutInflater.inflate(R.layout.menyu_chiqish, null)
        val yes1 = customLayout.findViewById<TextView>(R.id.btnPositive)
        yes1.setOnClickListener { b: View? -> finish() }
        val no1 = customLayout.findViewById<TextView>(R.id.btnNegative)
        no1.setOnClickListener { b: View? -> builder.hide() }
        builder.setView(customLayout)
        builder.setCancelable(false)
        builder.show()
    }

    @Override
    override fun onStop() {
        super.onStop()

    }
}