package cdu278.repetition

fun Repetition(
    id: Long,
    label: String,
    type: RepetitionType,
    data: RepetitionData,
    state: RepetitionState,
    hint: String?,
): Repetition {
    return RepetitionImpl(
        id,
        label,
        type,
        data,
        state,
        hint,
    )
}

private class RepetitionImpl(
    override val id: Long,
    override val label: String,
    override val type: RepetitionType,
    override val data: RepetitionData,
    override val state: RepetitionState,
    override val hint: String?,
) : Repetition