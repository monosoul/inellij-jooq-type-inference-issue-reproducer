package dev.monosoul.intellij.type.inference.issue

import dev.monosoul.intellij.type.inference.issue.generated.tables.references.FOO
import kotlinx.coroutines.reactive.asFlow
import org.jooq.Record
import org.jooq.ResultQuery
import org.jooq.impl.DSL
import org.reactivestreams.Publisher

fun main() {
    DSL.selectFrom(FOO)
        // IntelliJ highlights that as unresolved reference, but this code compiles
        .asFlow()

    DSL.selectFrom(FOO)
        .where(FOO.ID.eq(123))
        // IntelliJ highlights that as unresolved reference, but this code compiles
        .asFlow()

    // this doesn't get highlighted
    DSL.selectFrom(FOO)
        .asPublisher()
        .asFlow()
}

private fun <R : Record> ResultQuery<R>.asPublisher() = this as Publisher<R>