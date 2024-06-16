package midget17468.hash.s.repository

interface HashesRepository {

    suspend fun salt(): ByteArray
}