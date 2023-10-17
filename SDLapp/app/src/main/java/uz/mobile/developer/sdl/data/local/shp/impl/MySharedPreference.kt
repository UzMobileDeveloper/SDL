
import android.content.Context
import android.content.SharedPreferences

class MySharedPreference constructor(context: Context) {

    var hasAccount: Boolean
        get() = sharedPreferences.getBoolean("hasAccount", false)
        set(hasAccount) {
            editor.putBoolean("hasAccount", hasAccount).apply()
        }

    var language: String
        get() = sharedPreferences.getString("language", "uz").toString()
        set(language) {
            editor.putString("language", language).apply()
        }

    companion object {
        var mySharedPreference: MySharedPreference? = null
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor

        fun init(context: Context): MySharedPreference? {
            if (mySharedPreference == null) {
                mySharedPreference = MySharedPreference(context)
            }
            return mySharedPreference
        }

        fun getInstance() = mySharedPreference!!
    }

    init {
        sharedPreferences = context.getSharedPreferences("app_name", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}
