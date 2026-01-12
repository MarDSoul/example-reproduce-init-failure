package org.example

import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

private abstract class AbstractComponent : KoinComponent {
    abstract val someDep: SomeDep

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

private class RealComponent(
    override val someDep: SomeDep
) : AbstractComponent()

private val appModule = module {
    single { SomeDep("Dark Magic") }
    single<AbstractComponent> { RealComponent(get()) }
}

fun experimentConstructorKoin() {
    startKoin { modules(appModule) }
    val koin = getKoin()
    val someComponent = koin.get<AbstractComponent>()
    println(someComponent.someDep.value)
}