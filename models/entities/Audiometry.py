from sqlalchemy import Column, Integer, ForeignKey
from sqlalchemy.orm import relationship
from database.connections import Base

class Audiometry(Base):
    __tablename__ = 'audiometries'

    id = Column(Integer, primary_key=True, index=True)
    patient_id = Column(Integer, ForeignKey('users.id'))  # Relación con User

    user = relationship("User", back_populates="audiometries")  # Relación inversa con User
    audiometry_results = relationship("AudiometryResults", back_populates="audiometry")  # Ejemplo de otra relación
