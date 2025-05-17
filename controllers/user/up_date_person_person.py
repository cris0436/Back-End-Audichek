from abc import ABC , abstractmethod

class UpDatePerson(ABC):
    @abstractmethod
    def updatePerson(self,personInfo):
        pass

