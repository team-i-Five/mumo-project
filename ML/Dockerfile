# nmf_past
# Use an official Python runtime as a parent image
FROM python:3.8

# /app에서 작업
WORKDIR /app

# /app으로 싹다복사
COPY . /app

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Fast_API에서 작업
WORKDIR /app/Fast_API


# Make port 80 available to the world outside this container
EXPOSE 80


# Run app_nmf.py when the container launches
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "80", "--reload"]