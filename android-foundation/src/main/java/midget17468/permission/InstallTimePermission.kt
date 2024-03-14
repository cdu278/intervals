package midget17468.permission

class InstallTimePermission : Permission {

    override suspend fun isGranted(): Boolean {
        return true
    }
}