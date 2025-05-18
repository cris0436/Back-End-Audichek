from abc import ABC, abstractmethod

class GetFrequencyController(ABC):
    @abstractmethod
    def get_frequency(self, frecuency_value: int):
        """
        Get the frequency value from the database
        :param frecuency_value: Frequency value to get
        :return: Frequency object
        """
        pass