package com.example.ck_cmvvm.screen.community.create

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) -> {
                    showPermissionContextPopup()
                }

                else -> {
                    ActivityCompat.requestPermissions(
                        this@CreateCommunityActivity,
                        arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                        PERMISSION_REQUEST_CODE
                    )
                }
            }
        }

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val time = System.currentTimeMillis()
            val name = userInfo.userName

            selectedUri?.let { uri ->
                viewModel.uploadImageToFirebaseStorage(uri, title, content, time, name!!)
            } ?: run {
                // 선택된 이미지가 없을 때의 처리
            }
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
            PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 요청 결과에 ok가 있다면
                    startContentProvider() // 갤러리 실행
                } else { // 요청 결과에 ok가 없다면
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    data?.data?.let { uri ->
                        // 사용자가 선택한 이미지의 URI에 대한 지속적인 접근 권한을 요청
                        try {
                            contentResolver.takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            )
                        } catch (e: SecurityException) {
                            // 권한 요청 실패 처리
                            Toast.makeText(this, "권한 요청 실패", Toast.LENGTH_SHORT).show()
                        }

                        // 이미지를 화면에 표시
                        binding.photoImageView.setImageURI(uri)
                        selectedUri = uri
                    } ?: run {
                        Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                // ... 기타 다른 requestCode 처리 ...
            }
        } else {
            Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() { // 권한 동의x 를 누른 후 띄워지는 팝업
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_CODE)
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

    companion object{
        const val PERMISSION_REQUEST_CODE = 1010
        private const val REQUEST_CODE_PICK_IMAGE = 2020
    }
}