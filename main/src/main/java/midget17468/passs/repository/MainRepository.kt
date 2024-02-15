package midget17468.passs.repository

import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import midget17468.passs.flowable.Flowable
import midget17468.passs.model.domain.PasswordItem
import midget17468.passs.model.domain.PasswordType

class MainRepository {

    val readItems: Flowable<List<PasswordItem>>
        get() = Flowable {
            flowOf(
                listOf(
                    PasswordItem(
                        id = 1,
                        type = PasswordType.Sim(operator = "Tele2"),
                        nextCheckDate = LocalDateTime(year = 2024, monthNumber = 2, dayOfMonth = 16, hour = 12, minute = 0),
                    ),
                    PasswordItem(
                        id = 2,
                        type = PasswordType.Password(service = "04ko bomja"),
                        nextCheckDate = LocalDateTime(year = 2024, monthNumber = 2, dayOfMonth = 15, hour = 23, minute = 30),
                    ),
                    PasswordItem(
                        id = 3,
                        type = PasswordType.Sim(operator = "DpucH9"),
                        nextCheckDate = LocalDateTime(year = 2024, monthNumber = 2, dayOfMonth = 17, hour = 12, minute = 0),
                    ),
                )
            )
        }
}