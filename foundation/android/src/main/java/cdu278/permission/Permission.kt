package cdu278.permission

interface Permission {

    suspend fun isGranted(): Boolean
}