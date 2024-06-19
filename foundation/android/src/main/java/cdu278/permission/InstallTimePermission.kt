package cdu278.permission

class InstallTimePermission : Permission {

    override suspend fun isGranted(): Boolean {
        return true
    }
}