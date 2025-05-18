from abc import ABC, abstractmethod

class GetAudiometryController(ABC):
    @abstractmethod
    def get_audiometry(self, audiometry_id: str):
        """
        Get audiometry by ID
        :param audiometry_id: ID of the audiometry to retrieve
        :return: Audiometry object
        """
        pass
    @abstractmethod
    def getAllAudiometries(self, user_id: str):
        """
        Get all audiometries for a user
        :param user_id: ID of the user to retrieve audiometries for
        :return: List of Audiometry objects
        """
        pass
    def getAudiometryByUserId(self, user_id: str):
        """
        Get all audiometries for a user
        :param user_id: ID of the user to retrieve audiometries for
        :return: List of Audiometry objects
        """
        pass