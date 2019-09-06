package someday.com.testdagger2kt.nomvp.di

import java.lang.annotation.Documented
import javax.inject.Scope

@Scope
@Documented
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Singleton2()