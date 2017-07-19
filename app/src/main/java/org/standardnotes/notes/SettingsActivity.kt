package org.standardnotes.notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import org.standardnotes.notes.comms.ExportUtil

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().replace(R.id.list,
                SettingsFragment()).commit()
        export.setOnClickListener { exportData() }

        version.text = "v" + packageManager.getPackageInfo(packageName, 0).versionName
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO does this make sense here?
        ExportUtil.clearExports(this)
    }

    private fun exportData() {
        val listener: ExportUtil.ExportListener = object : ExportUtil.ExportListener {
            override fun onExportFailed() {
                Snackbar.make(root, R.string.error_fail_export, Snackbar.LENGTH_SHORT).show()
            }
        }
        if (radio_dec.isChecked)
            ExportUtil.exportDecrypted(this, listener)
        else
            ExportUtil.exportEncrypted(this, listener)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings)
        }
    }
}