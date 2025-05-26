from pydantic import BaseModel, Field
from datetime import datetime
class DecibelFrequency(BaseModel):
    decibel: float = Field(..., description="Decibel level in dB")
    frequency: float = Field(..., description="Frequency in Hz")
    ear: int = Field(..., description="Ear (0 for left, 1 for right)", ge=0, le=1)
    is_ear: bool = Field(..., description="Hearing (true or false)")
    
    class Config:
        json_schema_extra = {  # En Pydantic v2, `schema_extra` cambió a `json_schema_extra`
            "example": {
                "decibel": 25.0,
                "frequency": 1000.0,
                "ear": True,
                "is_ear": True
            }
        }

class Audiometry(BaseModel):
    user_id: int = Field(..., description="User ID")
    date: datetime = datetime.now()
    decibel_frequencies: list[DecibelFrequency] = Field(..., description="List of decibel frequencies")
    
    class Config:
        json_schema_extra = {
            "example": {
                "user_id": 123,
                "decibel_frequencies": [
                    {"decibel": 25.0, "frequency": 1000.0, "ear": True, "is_ear": True},
                    {"decibel": 30.0, "frequency": 2000.0, "ear":True, "is_ear": False}
                ]
            }
        }

class AudiometryResult(BaseModel):
    audiometry_score: float = Field(..., description="Audiometry score")
    user_id: int = Field(..., description="User ID")
    decibel_frequencies: list[DecibelFrequency] = Field(..., description="List of decibel frequencies")
    
    class Config:
        json_schema_extra = {
            "example": {
                "audiometry_score": 85.0,
                "user_id": 123,
                "decibel_frequencies": [
                    {"decibel": 25.0, "frequency": 1000.0, "ear": True, "is_ear": True},
                    {"decibel": 30.0, "frequency": 2000.0, "ear":True, "is_ear": False}
                ]
            }
        }
        
class AudiometryResultOut(BaseModel):
    frecuencias: list[int] = None
    decibelesDerecha : list[int]