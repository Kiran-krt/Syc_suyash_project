package com.syc.dashboard.framework.common.utils

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object PasswordUtils {

    private const val KEY: String = "SuyashKey1234567" // 16 char
    private const val PWD_CHARACTER_SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun getRandPassword(pwdLength: Int = 6): String {
        val random = Random(System.nanoTime())
        val password = StringBuilder()

        for (i in 0 until pwdLength) {
            val rIndex = random.nextInt(PWD_CHARACTER_SET.length)
            password.append(PWD_CHARACTER_SET[rIndex])
        }

        return password.toString()
    }

    fun encrypt(password: String): String {
        val (secretKeySpec, ivParameterSpec, cipher) = triple()
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

        val encryptedValue = cipher.doFinal(password.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedValue)
    }

    fun decrypt(password: String): String {
        val (secretKeySpec, ivParameterSpec, cipher) = triple()
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

        val decryptedByteValue = cipher.doFinal(Base64.getDecoder().decode(password))
        return String(decryptedByteValue)
    }

    private fun triple(): Triple<SecretKeySpec, GCMParameterSpec, Cipher> {
        val secretKeySpec = SecretKeySpec(KEY.toByteArray(), "AES")
        val iv = ByteArray(16)
        val charArray = KEY.toCharArray()
        for (i in charArray.indices) {
            iv[i] = charArray[i].code.toByte()
        }
        val ivParameterSpec = GCMParameterSpec(128, iv)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        return Triple(secretKeySpec, ivParameterSpec, cipher)
    }
}
