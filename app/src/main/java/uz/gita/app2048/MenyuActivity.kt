package uz.gita.app2048

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView

class MenyuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menyu)

        val pley = findViewById<ImageView>(R.id.pley)
        val about = findViewById<AppCompatImageView>(R.id.About)
        val score = findViewById<ImageView>(R.id.score)
        val share = findViewById<ImageView>(R.id.share)

        pley.setOnClickListener { v: View? ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
//


        about.setOnClickListener { v: View? ->
            val builder =
                AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
            val customLayout =
                layoutInflater.inflate(R.layout.abuit, null)
            val ok = customLayout.findViewById<AppCompatButton>(R.id.ok)
            ok.setOnClickListener { b: View? -> builder.hide()
            builder.dismiss()
                }
            builder.setView(customLayout)
            builder.setCancelable(false)
            builder.show()

        }
        score.setOnClickListener {

            val intent= Intent(this,ScoreActivity1::class.java)
            startActivity(intent)


        }
    }
    override fun onBackPressed(){
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
}