package ordertracker

class Renderer {

	def message (String message, boolean newline = true) {
		return newLine(message,newline)
	}

	def link (String method, String description, boolean newline = true) {
		def message = "<a href="+method+">"+method+"</a>"

		if ( description != "" )
			message += ": "+description

		return newLine(message, newline)
	}


	def newLine(String message, boolean newline) {
		if ( newline == true )
			return message + '<br>'
		else
			return message
	}
}
