from sqlalchemy import Column, Integer, String, Float, ForeignKey, DateTime, Boolean
from sqlalchemy.orm import relationship
from database import Base
import datetime
class User(Base):
    __tablename__ = "users"
    id = Column(Integer, primary_key=True, index=True)
    full_name = Column(String); email = Column(String, unique=True, index=True)
    phone_number = Column(String, unique=True, index=True); hashed_password = Column(String)
    is_active = Column(Boolean, default=True)
    alerts = relationship("EmergencyAlert", back_populates="user")
class EmergencyAlert(Base):
    __tablename__ = "emergency_alerts"
    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"))
    timestamp = Column(DateTime, default=datetime.datetime.utcnow)
    latitude = Column(Float); longitude = Column(Float); status = Column(String, default="ACTIVE")
    user = relationship("User", back_populates="alerts")
