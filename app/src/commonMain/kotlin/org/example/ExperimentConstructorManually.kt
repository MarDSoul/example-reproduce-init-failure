package org.example

private abstract class AbstractComponentMan {
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

private class RealComponentMan(
    override val someDep: SomeDep
) : AbstractComponentMan()

fun experimentConstructorMan() {
    val someDep = SomeDep("Dark Magic")
    val someComponent = RealComponentMan(someDep)
    println(someComponent.someDep.value)
}
