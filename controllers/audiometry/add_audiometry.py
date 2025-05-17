from abc import ABC, abstractmethod

class AddAudiometryController(ABC):
    @abstractmethod
    def add_audiometry(self, audiometry_data: dict):
        """
        Add a new audiometry
        :param audiometry_data: Data of the audiometry to add
        :return: Audiometry object
        """
        pass

    