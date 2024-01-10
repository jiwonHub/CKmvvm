package com.example.ck_cmvvm.screen.community.create

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.databinding.ActivityCommunityCreateBinding
import com.example.ck_cmvvm.screen.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCommunityActivity: BaseActivity<CreateCommunityViewModel, ActivityCommunityCreateBinding>() {

    private val sharedPreferencesRepository: SharedPreferencesRepository by lazy {
        SharedPreferencesRepository(this)
    }

    private var selectedUri: Uri? = null

    override val viewModel by viewModel<CreateCommunityViewModel>()

    override fun getViewBinding(): ActivityCommunityCreateBinding = ActivityCommunityCreateBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.uploadUriState.observe(this) {
        when(it){
            UploadUriState.Error -> handleError()
            UploadUriState.Loading -> handleLoading()
            UploadUriState.Success -> handleSuccess()
            else -> Unit
        }
    }

    private fun handleSuccess() {
        hideProgress()
        Toast.makeText(this, "이미지 업로드 성공!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun handleLoading() {
        showProgress()
    }

    private fun handleError() {
        hideProgress()
        Toast.makeText(this, "이미지 업로드 실패ㅜ", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun initViews() = with(binding) {
        super.initViews()
        val userInfo = sharedPreferencesRepository.getUserInfo()

        addImageButton.setOnClickListener {
            when{
                ContextCompat.checkSelfPermission(
                    this@CreateCommunityActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }

                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1010
                    )
                }
            }
        }

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val time = System.currentTimeMillis()
            val name = userInfo.userName

            viewModel.uploadCommunity(title, content, selectedUri.toString(), time, name!!)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onRequestPermissionsResult( // 권한 체크 시 그 결과를 확인하는 함수
        requestCode: Int,               // 요청할 때 보낸 코드
        permissions: Array<out String>,
        grantResults: IntArray          // 요청에 ok했을 때의 정보를 갖음.
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) { // 요청할 때 보낸 코드가 1010이면
            1010 ->
                if (grantResults.isNotEmpty()) { // 요청 결과에 ok가 있다면
                    startContentProvider() // 갤러리 실행
                } else { // 요청 결과에 ok가 없다면
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult( // 갤러리 실행 후 결과를 체크하는 함수
        requestCode: Int, // 갤러리 실행 시 보낸 코드
        resultCode: Int,  // 사진 가져오기가 성공 시 갖는 코드
        data: Intent?     // 사진
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) { // 성공하지 못했다면
            return
        }

        when (requestCode) {
            2020 -> {
                data?.data?.let { uri ->
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    binding.photoImageView.setImageURI(uri)
                    selectedUri = uri
                } ?: run {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        startActivityForResult(intent, 2020)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() { // 권한 동의x 를 누른 후 띄워지는 팝업
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }

    private fun showProgress() { // 로딩창 o
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() { // 로딩창 x
        binding.progressBar.isGone = true
    }
}