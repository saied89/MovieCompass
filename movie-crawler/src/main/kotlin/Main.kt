import di.NetwokModule
import org.koin.standalone.StandAloneContext.startKoin


fun main(vararg args: String){
    startKoin(listOf(NetwokModule))
}