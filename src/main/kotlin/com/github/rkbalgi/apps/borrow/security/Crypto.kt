package com.github.rkbalgi.apps.borrow.security

import org.apache.commons.codec.binary.Hex
import org.bouncycastle.crypto.digests.SHA256Digest


fun sha256(data: String): String {
    SHA256Digest().run {

        val res = ByteArray(this.digestSize)
        update(data.toByteArray(Charsets.UTF_8), 0, data.length)
        doFinal(res, 0)
        return Hex.encodeHexString(res)
    }
}

