package xyz.avalonxr.data.error

sealed class CommonError(message: String) : AvalonError(message) {

    data object GeneralError : CommonError("Exception encountered!")
}
