from sqlalchemy import Column, Integer, Boolean, ForeignKey
from sqlalchemy.orm import relationship
from database.Conections import Base

class AudiometryResults(Base):
    __tablename__ = 'audiometry_results'

    id = Column(Integer, primary_key=True, index=True)
    audiometry_id = Column(Integer, ForeignKey('audiometries.id'))
    frecuency_id = Column(Integer, ForeignKey('frequencies.id'))
    decibel_id = Column(Integer, ForeignKey('decibels.id'))
    right_ear = Column(Boolean, nullable=True)
    left_ear = Column(Boolean, nullable=True)

    audiometry = relationship("Audiometry", back_populates="audiometry_results")
    frecuency = relationship("Frecuency", back_populates="audiometry_results")
    decibel = relationship("Decibel", back_populates="audiometry_results")
