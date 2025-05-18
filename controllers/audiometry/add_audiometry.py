from abc import ABC, abstractmethod
from schemas.Audiometry import Audiometry as AudiometrySchema
class AddAudiometryController(ABC):
    @abstractmethod
    def add_audiometry(self, audiometry_data: AudiometrySchema):
        """
        Add a new audiometry
        :param audiometry_data: Data of the audiometry to add
        :return: Audiometry object
        """
        pass

    