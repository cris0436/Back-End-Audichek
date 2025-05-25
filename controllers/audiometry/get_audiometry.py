from abc import ABC, abstractmethod
from schemas.Audiometry import Audiometry as Audiometry_shema
class GetAudiometryController(ABC):

    @abstractmethod
    def getAllAudiometries(self):
        """
        Get all audiometries for a user
        :param user_id: ID of the user to retrieve audiometries for
        :return: List of Audiometry objects
        """
        pass

    @abstractmethod
    def getAudiometryByUserId(self, user_id: int):
        """
        Get all audiometries for a user
        :param user_id: ID of the user to retrieve audiometries for
        :return: List of Audiometry objects
        """
        pass