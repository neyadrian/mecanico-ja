import React, { useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  StatusBar,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
} from 'react-native';

export default function LoginScreen({ navigation }) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

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

            <Text style={styles.welcomeTitle}>Bem Vindo(a)</Text>
            <Text style={styles.welcomeSubtitle}>Entre e continue para o menu!</Text>
          </View>

          {/* CARD BRANCO (Formulário) */}
          <View style={styles.card}>
            
            {/* Campo E-mail */}
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

            {/* Campo Senha */}
            <Text style={styles.label}>Senha</Text>
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                placeholder="••••••••••••"
                placeholderTextColor="#A0AEC0"
                secureTextEntry
                value={senha}
                onChangeText={setSenha}
              />
            </View>

            {/* Esqueceu a Senha */}
            <TouchableOpacity style={styles.forgotButton}>
              <Text style={styles.forgotText}>Esqueceu a senha?</Text>
            </TouchableOpacity>

            {/* Botão Login */}
            <TouchableOpacity style={styles.loginButton} activeOpacity={0.8}>
              <Text style={styles.loginButtonText}>Login</Text>
            </TouchableOpacity>

            {/* Link de Cadastro */}
            <View style={styles.signupContainer}>
              <Text style={styles.signupText}>Não tem uma conta? </Text>
              <TouchableOpacity onPress={() => navigation.navigate('SignUp')}>
                <Text style={{ color: '#FF6224' }}>Cadastre-se</Text>
              </TouchableOpacity>
            </View>

          </View>

        </ScrollView>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

// ESTILOS DA TELA
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#132247',
  },
  scrollContainer: {
    flexGrow: 1,
  },
  // Cabeçalho
  header: {
    paddingHorizontal: 28,
    paddingTop: 50,
    paddingBottom: 30,
  },
  logoRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 28,
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
    fontSize: 32,
    fontWeight: 'bold',
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
    paddingTop: 36,
  },
  label: {
    color: '#334155',
    fontSize: 13,
    fontWeight: '600',
    marginBottom: 8,
    marginTop: 12,
  },
  inputContainer: {
    backgroundColor: '#FFFFFF',
    borderRadius: 12,
    borderWidth: 1,
    borderColor: '#E2E8F0',
    paddingHorizontal: 16,
    height: 52,
    justifyContent: 'center',
  },
  input: {
    fontSize: 15,
    color: '#0F172A',
  },
  forgotButton: {
    alignSelf: 'flex-end',
    marginTop: 12,
    marginBottom: 24,
  },
  forgotText: {
    color: '#FF6224',
    fontSize: 13,
    fontWeight: '600',
  },
  loginButton: {
    backgroundColor: '#FF6224',
    height: 52,
    borderRadius: 16,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 3, // Sombra no Android
    shadowColor: '#FF6224', // Sombra no iOS
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  loginButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  signupContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 32,
    marginBottom: 20,
  },
  signupText: {
    color: '#64748B',
    fontSize: 14,
  },
  signupLink: {
    color: '#FF6224',
    fontSize: 14,
    fontWeight: 'bold',
  },
});