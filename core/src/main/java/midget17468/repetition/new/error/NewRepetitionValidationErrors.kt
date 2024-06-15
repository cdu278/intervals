package midget17468.repetition.new.error

import midget17468.repetition.new.error.owner.EmptyLabelErrorOwner
import midget17468.repetition.new.error.owner.EmptyPasswordErrorOwner
import midget17468.repetition.new.error.owner.PasswordsDontMatchErrorOwner

interface NewRepetitionValidationErrors :
    EmptyLabelErrorOwner,
    EmptyPasswordErrorOwner,
    PasswordsDontMatchErrorOwner