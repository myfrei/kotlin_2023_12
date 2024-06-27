package exception

import models.FhCommand

class UnknownFhCommand(command: FhCommand) : Throwable("Wrong command $command at mapping toTransport stage")