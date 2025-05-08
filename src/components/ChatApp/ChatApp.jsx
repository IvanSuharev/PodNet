// ChatApp.jsx
import React, { useState, useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import './ChatApp.css';

const ChatApp = () => {
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState('');
    const [userId] = useState(Date.now().toString()); // Временный ID пользователя
    const stompClient = useRef(null);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8181/ws');
        stompClient.current = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            debug: (str) => console.log(str),
        });

        stompClient.current.onConnect = (frame) => {
            console.log('Connected: ' + frame);
            stompClient.current.subscribe(
                `/topic/message/${userId}`,
                (message) => {
                    const newMessage = JSON.parse(message.body);
                    setMessages(prev => [...prev, newMessage]);
                }
            );
        };

        stompClient.current.activate();

        return () => {
            stompClient.current.deactivate();
        };
    }, [userId]);

    const sendMessage = () => {
        if (inputMessage.trim()) {
            const message = {
                content: inputMessage,
                author: { id: userId },
                timestamp: new Date().toISOString()
            };

            stompClient.current.publish({
                destination: '/app/chat.send',
                body: JSON.stringify(message)
            });

            setInputMessage('');
        }
    };

    return (
        <div className="chat-container">
            <div className="chat-header">
                <h1>Podnet Messenger</h1>
                <div className="user-id">Your ID: {userId}</div>
            </div>

            <div className="chat-messages">
                {messages.map((msg, index) => (
                    <div
                        key={index}
                        className={`message ${msg.author.id === userId ? 'sent' : 'received'}`}
                    >
                        <div className="message-content">{msg.content}</div>
                        <div className="message-time">
                            {new Date(msg.timestamp).toLocaleTimeString()}
                        </div>
                    </div>
                ))}
            </div>

            <div className="chat-input">
                <input
                    type="text"
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
                    placeholder="Type a message..."
                />
                <button onClick={sendMessage}>
                    <svg className="send-icon" viewBox="0 0 24 24">
                        <path d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z"/>
                    </svg>
                </button>
            </div>
        </div>
    );
};

export default ChatApp;