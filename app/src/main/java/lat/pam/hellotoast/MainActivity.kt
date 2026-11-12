package lat.pam.hellotoast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    // ViewModel: datanya bertahan walaupun layar diputar
    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonSwitchPage = findViewById<Button>(R.id.button_switchpage)
        val buttonBrowser = findViewById<Button>(R.id.button_browser)

        // Observer: setiap nilai LiveData berubah, TextView ikut diperbarui
        val nameObserver = Observer<Int> { newCount ->
            mShowCount.text = newCount.toString()
        }
        model.currentName.observe(this, nameObserver)

        buttonCountUp.setOnClickListener(View.OnClickListener {
            val newCount = (model.currentName.value ?: 0) + 1
            model.currentName.setValue(newCount)
            Log.d("mCount", newCount.toString())
        })

        buttonToast.setOnClickListener(View.OnClickListener {
            val tulisan: String = mShowCount.text.toString()
            Toast.makeText(this, "Angka yang dimunculkan " + tulisan, Toast.LENGTH_LONG).show()
        })

        // Explicit Intent
        buttonSwitchPage.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        })

        // Implicit Intent
        buttonBrowser.setOnClickListener(View.OnClickListener {
            val intentbrowse = Intent(Intent.ACTION_VIEW)
            intentbrowse.setData(Uri.parse("https://www.google.com/"))
            startActivity(intentbrowse)
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}