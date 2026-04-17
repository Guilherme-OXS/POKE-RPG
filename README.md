# RPG Dungeon - Java Console

RPG de turno em console, com foco em Orientacao a Objetos e narrativa dinamica por rotas secretas.

## Objetivo do projeto

Este projeto foi desenvolvido para praticar conceitos de POO em Java com um jogo completo:

- Classe abstrata
- Heranca
- Polimorfismo
- Interfaces
- Encapsulamento
- Colecoes (ArrayList)
- Tratamento de excecoes com try/catch/finally

## Destaques do jogo

- Combate por turnos (jogador vs IA)
- IA inimiga melhorada (recupera apenas quando faz sentido)
- Sistema de itens com inventario e loja
- Dungeons com progressao por fases
- Boss final dinamico com rotas por nome do heroi
- ASCII Art personalizada para personagens e chefes
- Animacoes de ataque, habilidade, projeteis e impacto
- Historia com avancar manual (ENTER) para leitura
- Guia de habilidade em tempo real (requisitos + progresso)

## Requisitos

- Java JDK 8 ou superior
- Terminal com suporte a ANSI (PowerShell funciona)

## Como executar (PowerShell)

Dentro da pasta do projeto:

```powershell
if (Test-Path out) { Remove-Item out -Recurse -Force }
New-Item -ItemType Directory out | Out-Null
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out rpg.Main
```

## Controles do combate

- 1: Atacar
- 2: Defender
- 3: Habilidade Especial
- 4: Recuperar
- 5: Usar Item

## Requisitos de habilidade dos herois

- Guerreiro - Impacto de Aco
	- Energia >= 50
	- Furia >= 2
	- Vida <= 45%

- Mago - Ritual da Ruptura
	- Energia >= 65
	- Energia percentual >= 60%
	- Runas >= 2

- Arqueiro - Disparo Perfurante
	- Energia >= 48
	- Foco >= 3
	- Vida >= 25%

Observacao: durante o turno, o jogo mostra na tela requisitos, progresso e o que falta para liberar a habilidade.

## Rotas narrativas secretas (por nome)

- guilherme -> final contra IA
- gabriell ou lowran -> final contra Prova do Andre
- bruno -> final contra Melancia Ancestral
- enzo -> final contra A Verdade Sem Rosto
- felipe -> final contra Trompete do Abismo
- qualquer outro nome -> final padrao contra Rei Demonio de Cindral

## Estrutura de pastas

```text
src/rpg/
	Main.java
	engine/
		Combate.java
		Dungeon.java
		Loja.java
		Narrativa.java
	interfaces/
		HabilidadeEspecial.java
		Recuperavel.java
	model/
		PersonagemBase.java
		TipoPersonagem.java
		herois/
			Guerreiro.java
			Mago.java
			Arqueiro.java
		inimigos/
			Orc.java
			Goblin.java
			DragaoMenor.java
			ChefeMisterioso.java
		itens/
			Item.java
			PocaoVida.java
			PocaoEnergia.java
			Elixir.java
	utils/
		ConsoleUI.java
		AsciiArt.java
		AsciiAnimation.java
		AnimacoesChefes.java
```

## Preparar para GitHub (push)

Este repositorio ja usa arquivo .gitignore para nao subir build/arquivos locais.

Fluxo recomendado:

```powershell
git add .
git commit -m "feat: rpg console com narrativa dinamica e IA melhorada"
git branch -M main
git remote add origin <URL_DO_SEU_REPOSITORIO>
git push -u origin main
```

Se o remote ja existir, use apenas:

```powershell
git push
```

## Problemas comuns

- javac nao reconhecido:
	- Instale o JDK e adicione o Java ao PATH.
- Terminal sem cor:
	- Use PowerShell moderno ou terminal com suporte ANSI.
