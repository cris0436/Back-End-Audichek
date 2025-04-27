from sqlalchemy import Column, Integer, String, Date
from sqlalchemy.orm import relationship
from database.connections import Base

class Person(Base):
    __tablename__ = 'persons'
    __table_args__ = {'extend_existing': True}

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    email = Column(String(30), unique=True, index=True)
    role = Column(String(50), nullable=False)
    birth_date = Column(Date, nullable=False)

    # Relaciones
    user = relationship("User", back_populates="person", uselist=False)
    admin = relationship("Admin", back_populates="person", uselist=False)