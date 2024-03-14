package midget17468.permission

interface Permission {

    suspend fun isGranted(): Boolean
}