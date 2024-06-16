package midget17468.hash.s.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom

class AndroidHashesRepository(
    private val saltDataStore: DataStore<ByteArray?>
) : HashesRepository {

    override suspend fun salt(): ByteArray {
        return saltDataStore.updateData { current ->
            current ?: generatedSalt()
        }!!
    }

    private suspend fun generatedSalt(): ByteArray {
        return withContext(Dispatchers.Default) {
            ByteArray(16)
                .also { SecureRandom().nextBytes(it) }
        }
    }
}