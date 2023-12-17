package com.mammates.mammates_seller_v1.presentation.pages.main.account_setting

import android.content.Context
import android.net.Uri

sealed class AccountSettingEvent {
    data class OnChangeStoreName(val storeName: String) : AccountSettingEvent()
    data class OnChangeAddress(val address: String) : AccountSettingEvent()
    data class OnChangeName(val name: String) : AccountSettingEvent()
    data class OnChangeEmail(val email: String) : AccountSettingEvent()
    data class OnChangeProfileImage(val uri: Uri) : AccountSettingEvent()
    data object OnDismissDialog : AccountSettingEvent()
    data object OnOpenConfirmDialog : AccountSettingEvent()
    data class OnConfirmChangesAccount(val context: Context) : AccountSettingEvent()
    data object ClearToken : AccountSettingEvent()

}