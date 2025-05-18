from sqlalchemy import Column, Integer, Boolean, ForeignKey
from sqlalchemy.orm import relationship
from models.persistence import Base

class AudiometryResults(Base):
    __tablename__ = 'audiometry_results'

    id = Column(Integer, primary_key=True, index=True)
    audiometry_id = Column(Integer, ForeignKey('audiometries.id'))
    frecuency_id = Column(Integer, ForeignKey('frequencies.id'))
    decibel_id = Column(Integer, ForeignKey('decibels.id'))
    ear=Column(Boolean, nullable=False)  # True for right ear, False for left ear
    is_ear=Column(Boolean, nullable=False)  # True if the patient heard the sound, False otherwise

    audiometry = relationship("Audiometry", back_populates="audiometry_results")
    frecuency = relationship("Frecuency", back_populates="audiometry_results")
    decibel = relationship("Decibel", back_populates="audiometry_results")
