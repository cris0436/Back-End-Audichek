from sqlalchemy import Column, Integer
from sqlalchemy.orm import relationship
from database.connections import Base

class Decibel(Base):
    __tablename__ = 'decibels'

    id = Column(Integer, primary_key=True, index=True)
    value = Column(Integer, nullable=False)

    audiometry_results = relationship("AudiometryResults", back_populates="decibel")
