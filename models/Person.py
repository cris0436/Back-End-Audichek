from sqlalchemy import Column, Integer, String, Date
from sqlalchemy.orm import relationship
from database.Conections import Base


class Person(Base):
    __tablename__ = 'persons'

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    email = Column(String(30), unique=True, index=True)
    role = Column(String(50), nullable=False)  # Asegúrate de especificar la longitud
    birth_date = Column(Date, nullable=False)

    # Relación con los usuarios
    user = relationship("Usuario", back_populates="person", uselist=False)
