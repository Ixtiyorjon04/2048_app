package uz.gita.app2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import uz.gita.app2048.contracts.TinyDB
import java.util.*

class ScoreActivity1 : AppCompatActivity() {

    private lateinit var  tinyDB: TinyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score1)
        tinyDB= TinyDB(this)
        val text1=findViewById<TextView>(R.id.Score_Stepes1_text)
        val text2=findViewById<TextView>(R.id.Score_Stepes2_text)
        val text3=findViewById<TextView>(R.id.Score_Stepes3_text)
        findViewById<ImageView>(R.id.okey).setOnClickListener { v: View? -> finish() }
        var list= tinyDB.getListInt("Scores")

            tinyDB.putListInt("Scores",list)

        Log.d("y",list.size.toString())
        Collections.sort(list)
        if (list.size>=1) {
            text1.text = list[list.size-1].toString()
        }
        if (list.size>=2) {
            text2.text = list[list.size-2].toString()
        }
        if (list.size>=3) {
            text3.text = list[list.size-3].toString()
        }

        var list2= ArrayList<Int>(3)



    }
}