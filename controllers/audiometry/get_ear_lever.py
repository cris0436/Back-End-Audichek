from abc import ABC, abstractmethod
from schemas.Audiometry import Audiometry as Audiometry_shema
class GetEarLeverController(ABC):
    """
    Abstract class to get the ear lever for a given ear.
    """

    @abstractmethod
    def get_ear_level(self, audiometry_data: Audiometry_shema, age: int) -> list:
        """
        Get the ear lever for a given ear.

        :param ear: The ear to get the lever for.
        :return: The ear lever.
        """
        pass
    @abstractmethod
    def getAge(self, user_id: int) -> int:
        pass