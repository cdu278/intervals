package midget17468.hash.salt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

val Context.hashSaltDataStore: DataStore<ByteArray?>
        by dataStore(
            fileName = "salt",
            serializer = SaltSerializer(),
        )

private class SaltSerializer : Serializer<ByteArray?> {

    override val defaultValue: ByteArray?
        get() = null

    override suspend fun readFrom(input: InputStream): ByteArray? {
        return withContext(Dispatchers.IO) {
            input
                .readBytes()
                .takeIf { it.isNotEmpty() }
        }
    }

    override suspend fun writeTo(t: ByteArray?, output: OutputStream) {
        withContext(Dispatchers.IO) {
            t?.let { output.write(it) }
        }
    }
}