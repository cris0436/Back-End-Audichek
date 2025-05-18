from abc import ABC, abstractmethod
from schemas.Audiometry import DecibelFrequency

class AddAudiometryResults(ABC):
    """
    Abstract class for adding audiometry results.
    """

    @abstractmethod
    def add_audiometry_result(self, patient_id: int, audiometry_data: DecibelFrequency) -> bool:
        """
        Add audiometry results for a patient.

        :param patient_id: ID of the patient.
        :param audiometry_data: Dictionary containing audiometry data.
        :return: True if the operation was successful, False otherwise.
        """
        pass