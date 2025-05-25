from abc import ABC, abstractmethod

class GetDecibelController(ABC):
    @abstractmethod
    def get_decibel(self, decibel_value: int):
        """
        Get the decibel value from the database
        :param decibel_value: Decibel value to get
        :return: Decibel object
        """
        pass