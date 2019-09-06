package someday.com.testdagger2kt.nomvp

import javax.inject.Inject

class People @Inject constructor() {
    fun hello(){
        println("ssssssshello")
    }
}

class People2 @Inject constructor() {
    fun hello(){
        println("ssssssshello2")
    }
}