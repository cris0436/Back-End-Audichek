from controllers.audiometry.get_audiometry import GetAudiometryController
from sqlalchemy.orm import Session
from schemas.Audiometry import Audiometry , DecibelFrequency
import math
class GetAudiometryControllerImp(GetAudiometryController):
    def __init__(self, audiometry_service):
        self.audiometry_service = audiometry_service
        
    def exponential_function(self, x: float) -> float:
        return 6.2971 * math.exp(0.0001 * x)

    def get_ear_level_65(self, audiometry_data: Audiometry, age: int) -> float:
        frecuency = 0.000004167 * (age)**4 - 0.0006833 * (age)**3 + 0.0375 * (age)**2 - 0.73492 * (age) + 20
        frecuency_not_ear = max(
            (i.frequency for i in audiometry_data.decibel_frequencies if not i.is_ear),
            default=0
        )

        if frecuency_not_ear == 0:
            return 100  # Perfecto estado auditivo

        return max(0, (frecuency - (frecuency_not_ear + 2000)) * 100 / frecuency)  # Evitar negativos

    def get_ear_level(self, audiometry_data: Audiometry, age: int) -> float:
        if age < 0:
            raise ValueError("Age cannot be negative")

        if age <= 65:
            return self.get_ear_level_65(audiometry_data, age)
        else:
            value = 100
            for i in audiometry_data.decibel_frequencies:
                if i.is_ear:
                    decibel_function = self.exponential_function(i.frequency)
                    deviation = max(0, decibel_function - i.decibel)  # Evitar negativos
                    value = (value + (deviation * 100 / decibel_function)) / 2  # Promedio ajustado

            return max(0, value)  # Asegurar que el score no sea negativo
                    
    
    def get_audiometryResult(self, audiometry_id: str):
        pass
    def getAllAudiometries(self):
        pass
    def getAudiometryByUserId(self, user_id: str):
        pass
    
