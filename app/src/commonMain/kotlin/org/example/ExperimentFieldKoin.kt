package org.example

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

private abstract class AbstractComponentField : KoinComponent {
    val someDep: SomeDep by inject()

    init {
        println("AbstractComponent:init:start")

        println("Boom?")
        try {
            println(someDep.value)
        } catch (t: Throwable) {
            println(t.message)
        }

        println("AbstractComponent:init:end")
    }
}

private class RealComponentField(
) : AbstractComponentField()

private val appModule = module {
    single { SomeDep("Dark Magic") }
    single<AbstractComponentField> { RealComponentField() }
}

fun experimentFieldKoin() {
    startKoin { modules(appModule) }
    val koin = getKoin()
    val someComponent = koin.get<AbstractComponentField>()
    println(someComponent.someDep.value)
}
