package can.co.id.uplaodfileexample.network.response

import java.io.Serializable

class DefaultResponse :Serializable {
    var error:Boolean = false
    var msg:String = ""
}