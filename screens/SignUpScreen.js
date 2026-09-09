import React, { useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  StatusBar,
  ScrollView,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';

export default function SignUpScreen({ navigation }) {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleRegister = () => {
    if (!fullName || !email || !password || !confirmPassword) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    if (password !== confirmPassword) {
      alert('As senhas não coincidem!');
      return;
    }

    const userData = {
      fullName,
      email,
      phone,
      password,
    };

    console.log('Dados do cadastro:', userData);
    alert('Cadastro realizado com sucesso!');
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#132247" />

      <KeyboardAvoidingView
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={{ flex: 1 }}
      >
        <ScrollView contentContainerStyle={styles.scrollContainer}>
          
          {/* HEADER (Parte Azul) */}
          <View style={styles.header}>
            <View style={styles.logoRow}>
              <View style={styles.logoIcon} />
              <Text style={styles.logoText}>MECÂNICO JÁ</Text>
            </View>

            <Text style={styles.welcomeTitle}>Crie sua conta</Text>
            <Text style={styles.welcomeSubtitle}>Preencha os dados para começar!</Text>
          </View>

          {/* CARD BRANCO (Formulário) */}
          <View style={styles.card}>

            {/* Nome Completo */}
            <Text style={styles.label}>Nome Completo</Text>
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                placeholder="Seu nome completo"
                placeholderTextColor="#A0AEC0"
                value={fullName}
                onChangeText={setFullName}
              />
            </View>

            {/* E-mail */}
            <Text style={styles.label}>Email</Text>
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                placeholder="seuemail@exemplo.com"
                placeholderTextColor="#A0AEC0"
                keyboardType="email-address"
                autoCapitalize="none"
                value={email}
                onChangeText={setEmail}
              />
            </View>

            {/* Telefone */}
            <Text style={styles.label}>Telefone / WhatsApp</Text>
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                placeholder="(00) 00000-0000"
                placeholderTextColor="#A0AEC0"
                keyboardType="phone-pad"
                value={phone}
                onChangeText={setPhone}
              />
            </View>

            {/* Senha */}
            <Text style={styles.label}>Senha</Text>
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                placeholder="••••••••••••"
                placeholderTextColor="#A0AEC0"
                secureTextEntry
                value={password}
                onChangeText={setPassword}
              />
            </View>

            {/* Confirmar Senha */}
            <Text style={styles.label}>Confirmar Senha</Text>
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                placeholder="••••••••••••"
                placeholderTextColor="#A0AEC0"
                secureTextEntry
                value={confirmPassword}
                onChangeText={setConfirmPassword}
              />
            </View>

            {/* Botão Cadastrar */}
            <TouchableOpacity 
              style={styles.registerButton} 
              activeOpacity={0.8}
              onPress={handleRegister}
            >
              <Text style={styles.registerButtonText}>Cadastrar</Text>
            </TouchableOpacity>

            {/* Link para Login Centralizado */}
            <View style={styles.loginContainer}>
              <Text style={styles.loginText}>Já tem uma conta? </Text>
              <TouchableOpacity onPress={() => navigation.navigate('Login')}>
                <Text style={styles.loginLink}>Faça Login</Text>
              </TouchableOpacity>
            </View>

          </View>

        </ScrollView>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#132247',
  },
  scrollContainer: {
    flexGrow: 1,
  },
  // Cabeçalho Azul
  header: {
    paddingHorizontal: 28,
    paddingTop: 50,
    paddingBottom: 24,
  },
  logoRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
  },
  logoIcon: {
    width: 32,
    height: 32,
    backgroundColor: '#FF6224',
    borderRadius: 8,
    marginRight: 12,
  },
  logoText: {
    color: '#FFFFFF',
    fontSize: 18,
    fontWeight: 'bold',
    letterSpacing: 1,
  },
  welcomeTitle: {
    color: '#FFFFFF',
    fontSize: 28,
    fontWeight: 'bold',
    marginTop: 12,
    marginBottom: 6,
  },
  welcomeSubtitle: {
    color: '#94A3B8',
    fontSize: 14,
  },
  // Card Branco
  card: {
    flex: 1,
    backgroundColor: '#F8FAFC',
    borderTopLeftRadius: 32,
    borderTopRightRadius: 32,
    paddingHorizontal: 28,
    paddingTop: 28,
    paddingBottom: 36,
  },
  label: {
    color: '#334155',
    fontSize: 13,
    fontWeight: '600',
    marginBottom: 6,
    marginTop: 10,
  },
  inputContainer: {
    backgroundColor: '#FFFFFF',
    borderRadius: 12,
    borderWidth: 1,
    borderColor: '#E2E8F0',
    paddingHorizontal: 16,
    height: 48,
    justifyContent: 'center',
  },
  input: {
    fontSize: 15,
    color: '#0F172A',
  },
  registerButton: {
    backgroundColor: '#FF6224',
    height: 52,
    borderRadius: 16,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 24,
    elevation: 3,
    shadowColor: '#FF6224',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  registerButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  loginContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 24,
    marginBottom: 12,
  },
  loginText: {
    color: '#64748B',
    fontSize: 14,
  },
  loginLink: {
    color: '#FF6224',
    fontSize: 14,
    fontWeight: 'bold',
  },
});