from pydantic import BaseModel, Field

class DecibelFrequency(BaseModel):
    level: float = Field(..., description="Decibel level in dB")
    frequency: float = Field(..., description="Frequency in Hz")
    ear: str = Field(..., description="Ear (left or right)")

    class Config:
        schema_extra = {
            "example": {
                "level": 25.0,
                "frequency": 1000.0,
                "ear": "left"
            }
        }
class Audiometry(BaseModel):
    user_id: int = Field(..., description="User ID")
    date: str = Field(..., description="Date of the audiometry")
    decibel_frequencies: list[DecibelFrequency] = Field(..., description="List of decibel frequencies")
    class Config:
        schema_extra = {
            "example": {
                "id": 1,
                "user_id": 123,
                "date": "2023-10-01",
                "decibel_frequencies": [
                    {"level": 25.0, "frequency": 1000.0, "ear": "left"},
                    {"level": 30.0, "frequency": 2000.0, "ear": "right"}
                ]
            }
        }